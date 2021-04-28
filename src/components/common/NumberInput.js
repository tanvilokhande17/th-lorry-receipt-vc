import React from "react";
import {FormControl} from "react-bootstrap";

export const NumberInput = ({value, setValue}) => {
  return (
    <FormControl
      autoFocus
      type="number"
      value={value}
      onChange={e => setValue(e.target.value)}
    />
  );
};
