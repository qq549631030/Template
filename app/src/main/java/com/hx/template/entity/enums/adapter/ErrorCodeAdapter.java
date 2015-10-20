package com.hx.template.entity.enums.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hx.template.entity.enums.ErrorCode;

import java.io.IOException;

public class ErrorCodeAdapter extends TypeAdapter<ErrorCode> {
	@Override
	public ErrorCode read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String id = reader.nextString();
		return ErrorCode.getInstance(id);
	}

	@Override
	public void write(JsonWriter writer, ErrorCode object) throws IOException {
		if (object == null) {
			writer.nullValue();
			return;
		}
		writer.value(object.getId());
	}

}
