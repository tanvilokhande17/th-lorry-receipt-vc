import React from "react";
import {Form} from "react-bootstrap";

export const NumberInput = ({value, setValue, placeholder}) => {
  return (
    <Form.Control
      autoFocus
      type="number"
      value={value}
      placeholder={placeholder}
      onChange={e => setValue(e.target.value)}
    />
  );
};
