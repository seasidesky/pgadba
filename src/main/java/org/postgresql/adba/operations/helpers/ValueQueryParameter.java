package org.postgresql.adba.operations.helpers;

import jdk.incubator.sql2.SqlType;
import org.postgresql.adba.communication.packets.parts.PgAdbaType;

public class ValueQueryParameter implements QueryParameter {
  private PgAdbaType type;
  private Object value;

  /**
   * parameter that represent one value.
   * @param value the value
   */
  public ValueQueryParameter(Object value) {
    this.value = value;

    if (value == null) {
      type = PgAdbaType.NULL;
    } else {
      type = PgAdbaType.guessTypeFromClass(value.getClass());
    }
  }

  /**
   * parameter that represent one value.
   * @param value the value
   * @param type the type of the value
   */
  public ValueQueryParameter(Object value, SqlType type) {
    this.value = value;
    if (type != null) {
      this.type = PgAdbaType.convert(type);
    } else {
      if (value == null) {
        this.type = PgAdbaType.NULL;
      } else {
        this.type = PgAdbaType.guessTypeFromClass(value.getClass());
      }
    }
  }

  @Override
  public int getOid() {
    return type.getOid();
  }

  @Override
  public short getParameterFormatCode() {
    return type.getFormatCodeTypes().getCode();
  }

  @Override
  public byte[] getParameter(int index) {
    return type.getByteGenerator().apply(value);
  }

  @Override
  public int numberOfQueryRepetitions() {
    return 1;
  }
}
