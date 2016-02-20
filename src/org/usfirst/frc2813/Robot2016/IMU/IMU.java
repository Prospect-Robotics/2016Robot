package org.usfirst.frc2813.Robot2016.IMU;

import java.util.Arrays;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class IMU extends SensorBase implements LiveWindowSendable,
		Runnable {
	static final int YAW_HISTORY_LENGTH = 10;
	static final byte DEFAULT_UPDATE_RATE_HZ = 100;
	static final short DEFAULT_ACCEL_FSR_G = 2;
	static final short DEFAULT_GYRO_FSR_DPS = 2000;

	SerialPort serial_port;
	float yaw_history[];
	int next_yaw_history_index;
	double user_yaw_offset;
	ITable m_table;
	Thread m_thread;
	protected byte update_rate_hz;

	volatile float yaw;
	volatile float pitch;
	volatile float roll;
	volatile float compass_heading;
	volatile int update_count = 0;
	volatile int byte_count = 0;
	volatile float nav6_yaw_offset_degrees;
	volatile short accel_fsr_g;
	volatile short gyro_fsr_dps;
	volatile short flags;

	double last_update_time;
	boolean stop = false;
	private IMUProtocol.YPRUpdate ypr_update_data;
	protected byte update_type = IMUProtocol.MSGID_YPR_UPDATE;

	public IMU(SerialPort serial_port, byte update_rate_hz) {
		ypr_update_data = new IMUProtocol.YPRUpdate();
		this.update_rate_hz = update_rate_hz;
		flags = 0;
		accel_fsr_g = DEFAULT_ACCEL_FSR_G;
		gyro_fsr_dps = DEFAULT_GYRO_FSR_DPS;
		this.serial_port = serial_port;
		yaw_history = new float[YAW_HISTORY_LENGTH];
		yaw = (float) 0.0;
		pitch = (float) 0.0;
		roll = (float) 0.0;
		try {
			serial_port.reset();
		} catch (RuntimeException e) {
		}
		initIMU();
		m_thread = new Thread(this);
		m_thread.start();
	}

	public IMU(SerialPort serial_port) {
		this(serial_port, DEFAULT_UPDATE_RATE_HZ);
	}

	protected void initIMU() {

		initializeYawHistory();
		user_yaw_offset = 0;

		byte stream_command_buffer[] = new byte[256];
		int packet_length = IMUProtocol.encodeStreamCommand(
				stream_command_buffer, update_type, update_rate_hz);
		try {
			serial_port.write(stream_command_buffer, packet_length);
		} catch (RuntimeException e) {
		}
	}

	protected void setStreamResponse(IMUProtocol.StreamResponse response) {

		flags = response.flags;
		nav6_yaw_offset_degrees = response.yaw_offset_degrees;
		accel_fsr_g = response.accel_fsr_g;
		gyro_fsr_dps = response.gyro_fsr_dps;
		update_rate_hz = (byte) response.update_rate_hz;
	}

	private void initializeYawHistory() {

		Arrays.fill(yaw_history, 0);
		next_yaw_history_index = 0;
		last_update_time = 0.0;
	}

	private void setYawPitchRoll(float yaw, float pitch, float roll,
			float compass_heading) {

		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.compass_heading = compass_heading;

		updateYawHistory(this.yaw);
	}

	protected void updateYawHistory(float curr_yaw) {

		if (next_yaw_history_index >= YAW_HISTORY_LENGTH) {
			next_yaw_history_index = 0;
		}
		yaw_history[next_yaw_history_index] = curr_yaw;
		last_update_time = Timer.getFPGATimestamp();
		next_yaw_history_index++;
	}

	private double getAverageFromYawHistory() {

		double yaw_history_sum = 0.0;
		for (int i = 0; i < YAW_HISTORY_LENGTH; i++) {
			yaw_history_sum += yaw_history[i];
		}
		double yaw_history_avg = yaw_history_sum / YAW_HISTORY_LENGTH;
		return yaw_history_avg;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public float getYaw() {
		float calculated_yaw = (float) (this.yaw - user_yaw_offset);
		if (calculated_yaw < -180) {
			calculated_yaw += 360;
		}
		if (calculated_yaw > 180) {
			calculated_yaw -= 360;
		}
		return calculated_yaw;
	}

	public float getCompassHeading() {
		return compass_heading;
	}

	public void zeroYaw() {
		user_yaw_offset = getAverageFromYawHistory();
	}

	public boolean isConnected() {
		double time_since_last_update = Timer.getFPGATimestamp()
				- this.last_update_time;
		return time_since_last_update <= 1.0;
	}

	public double getByteCount() {
		return byte_count;
	}

	public double getUpdateCount() {
		return update_count;
	}

	public boolean isCalibrating() {
		short calibration_state = (short) (this.flags & IMUProtocol.NAV6_FLAG_MASK_CALIBRATION_STATE);
		return (calibration_state != IMUProtocol.NAV6_CALIBRATION_STATE_COMPLETE);
	}

	public double pidGet() {
		return getYaw();
	}

	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Value", getYaw());
		}
	}

	public void startLiveWindowMode() {
	}

	public void stopLiveWindowMode() {
	}

	public void initTable(ITable itable) {
		m_table = itable;
		updateTable();
	}

	public ITable getTable() {
		return m_table;
	}

	public String getSmartDashboardType() {
		return "Gyro";
	}

	protected int decodePacketHandler(byte[] received_data, int offset,
			int bytes_remaining) {

		int packet_length = IMUProtocol.decodeYPRUpdate(received_data, offset,
				bytes_remaining, ypr_update_data);
		if (packet_length > 0) {
			setYawPitchRoll(ypr_update_data.yaw, ypr_update_data.pitch,
					ypr_update_data.roll, ypr_update_data.compass_heading);
		}
		return packet_length;
	}

	public void run() {

		stop = false;
		boolean stream_response_received = false;
		double last_stream_command_sent_timestamp = 0.0;
		try {
			serial_port.setReadBufferSize(512);
			serial_port.setTimeout(1.0);
			serial_port.enableTermination('\n');
			serial_port.flush();
			serial_port.reset();
		} catch (RuntimeException e) {
		}

		IMUProtocol.StreamResponse response = new IMUProtocol.StreamResponse();

		byte[] stream_command = new byte[256];

		int cmd_packet_length = IMUProtocol.encodeStreamCommand(stream_command,
				update_type, update_rate_hz);
		try {
			serial_port.reset();
			serial_port.write(stream_command, cmd_packet_length);
			serial_port.flush();
			last_stream_command_sent_timestamp = Timer.getFPGATimestamp();
		} catch (RuntimeException e) {
		}

		while (!stop) {
			try {
				while (!stop && (serial_port.getBytesReceived() < 1)) {
					Timer.delay(0.1);
				}

				int packets_received = 0;
				byte[] received_data = serial_port.read(256);
				int bytes_read = received_data.length;
				if (bytes_read > 0) {
					byte_count += bytes_read;
					int i = 0;
					while (i < bytes_read) {

						int bytes_remaining = bytes_read - i;
						int packet_length = decodePacketHandler(received_data,
								i, bytes_remaining);
						if (packet_length > 0) {
							packets_received++;
							update_count++;
							i += packet_length;
						} else {
							packet_length = IMUProtocol
									.decodeStreamResponse(received_data, i,
											bytes_remaining, response);
							if (packet_length > 0) {
								packets_received++;
								setStreamResponse(response);
								stream_response_received = true;
								i += packet_length;
							} else {
								i++;
							}
						}
					}

					if ((packets_received == 0) && (bytes_read == 256)) {
						serial_port.reset();
					}

					if (!stream_response_received
							&& ((Timer.getFPGATimestamp() - last_stream_command_sent_timestamp) > 3.0)) {
						cmd_packet_length = IMUProtocol.encodeStreamCommand(
								stream_command, update_type, update_rate_hz);
						try {
							last_stream_command_sent_timestamp = Timer
									.getFPGATimestamp();
							serial_port
									.write(stream_command, cmd_packet_length);
							serial_port.flush();
						} catch (RuntimeException e) {
						}
					} else {
						if (stream_response_received
								&& (serial_port.getBytesReceived() == 0)) {
							Timer.delay(1.0 / update_rate_hz);
						}
					}
				}
			} catch (RuntimeException e) {
				stream_response_received = false;
			}
		}
	}
}