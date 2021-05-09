import React from "react";
import {Form} from "react-bootstrap";

export const TextInput = ({value, setValue, placeholder}) => {
  return (
    <Form.Control
      autoComplete="new-password"
      autoFocus
      type="text"
      value={value}
      placeholder={placeholder}
      onChange={e => setValue(e.target.value)}
    />
  );
};
