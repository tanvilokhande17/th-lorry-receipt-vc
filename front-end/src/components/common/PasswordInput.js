import React from "react";
import {Form} from "react-bootstrap";

export const PasswordInput = ({value, setValue}) => {
  return (
    <Form.Control
      autoFocus
      autoComplete="new-password"
      type="password"
      value={value}
      onChange={e => setValue(e.target.value)}
    />
  );
};
