import React from "react";
import {FormControl} from "react-bootstrap";

export const PasswordInput = ({value, setValue}) => {
  return (
    <FormControl
      autoFocus
      autoComplete="new-password"
      type="password"
      value={value}
      onChange={e => setValue(e.target.value)}
    />
  );
};
