import React from "react";
import {Button, Dropdown, DropdownButton, Table} from "react-bootstrap";
import {useLocation} from "react-router";
import {Label} from "../common/Label";
import LorryReceipt from "./LorryReceipt";

const LRDetails = () => {
  const location = useLocation();
  const lrDetails = location.state.lrDetails;
  return (
    <div className="lrdetails-container">
      <div className="heading">Lorry Receipt Details</div>
      {lrDetails ? <LorryReceipt lrDetails={lrDetails} /> : null}
    </div>
  );
};

export default LRDetails;
