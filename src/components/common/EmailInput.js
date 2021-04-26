import React from "react";
import {FormControl} from "react-bootstrap";

export const EmailInput = ({value, setValue}) => {
  return (
    <FormControl
      autoFocus
      type="text"
      value={value}
      onChange={e => setValue(e.target.value)}
    />
  );
};
