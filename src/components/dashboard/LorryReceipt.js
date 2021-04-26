import React from "react";
import icon from "./../../util/images/icon2.jpg";

const LorryReceipt = ({lrDetails}) => {
  return (
    <>
      {lrDetails ? (
        <div className="lrDetails-content">
          <div className="lrDetails-header">
            <div style={{flex: 1}}>
              <img style={{maxHeight: 75}} src={icon} />
            </div>

            <div className="lrDetails-company">
              Matarani Transport Service
              <div className="lrDetails-address">
                Office: Main Market Road,Bandra,Maharashtra
                <div>Vehicle No : 62893721</div>
              </div>
            </div>

            <div style={{flex: 1, textAlign: "end"}}>
              <div>Mob No: 396493874</div> <div>LR No: {lrDetails.lrNo}</div>
              <div>VC ID: {lrDetails.vcId}</div>
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <div style={{flex: 0.8}}>Consignor:</div>
            <div style={{flex: 3}}>{lrDetails.consigner}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              Date: {lrDetails.date}
            </div>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 0.8}}>Consignee:</div>
            <div style={{flex: 3}}>{lrDetails.consignee}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              Status: {lrDetails.status}
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1}}>Place of Loading:</div>
            <div style={{flex: 5}}>Azhad Nagar Road,Andheri,Mumbai</div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1}}>Destination:</div>
            <div style={{flex: 5}}>St. Peter Road,Santacruz,Mumbai</div>
          </div>
        </div>
      ) : null}
    </>
  );
};

export default LorryReceipt;
