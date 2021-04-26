import React from "react";
import {FormControl} from "react-bootstrap";

export const TextInput = ({value, setValue}) => {
  return (
    <FormControl
      autoComplete="new-password"
      autoFocus
      type="text"
      value={value}
      onChange={e => setValue(e.target.value)}
    />
  );
};
