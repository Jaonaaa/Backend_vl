package com.popo.utils;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Status {

	public String status;

	public String message;

	public Object data;

	public List<String> message_list;

	public Status ok_(String message, Object data) {
		return Status.builder().status("ok").message(message).data(data).build();
	}

	public Status error_(String message, Object data) {
		return Status.builder().status("error").message(message).data(data).build();
	}

	public static Status error(String message, Object data) {
		return Status.builder().status("error").message(message).data(data).build();
	}

	public static Status error(List<String> messages, Object data) {
		return Status.builder().status("error").message_list(messages).data(data).build();
	}

	public static Status ok(String message, Object data) {
		return Status.builder().status("ok").message(message).data(data).build();
	}

	public static Status ok(List<String> messages, Object data) {
		return Status.builder().status("ok").message_list(messages).data(data).build();
	}

	public static Double getPercent(Double value, Double percent) {
		if (value == 0 || percent == 0)
			return 0.0;
		return percent * value / 100;
	}

}
