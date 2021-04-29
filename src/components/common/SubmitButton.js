import React from "react";
import {Button} from "react-bootstrap";

export const SubmitButton = ({label, disabled, submitHandler}) => {
  return (
    <Button variant="primary" disabled={disabled} block onClick={submitHandler}>
      {label}
    </Button>
  );
};
