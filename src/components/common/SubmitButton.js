import React from "react";
import {Button} from "react-bootstrap";

export const SubmitButton = ({label, submitHandler}) => {
  return (
    <Button
      variant="primary"
      block
      onClick={submitHandler}
      type="submit"
    >
      {label}
    </Button>
  );
};
