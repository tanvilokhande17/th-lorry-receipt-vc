import React from "react";
import {useLocation} from "react-router";
import LorryReceipt from "./LorryReceipt";

const LRDetails = () => {
  const location = useLocation();
  const lrDetails = location.state.lrDetails;
  return (
    <div className="lrdetails-container">
      <div className="lrdetails-header">Lorry Receipt Details</div>
      {lrDetails ? <LorryReceipt lrDetails={lrDetails} /> : null}
    </div>
  );
};

export default LRDetails;
