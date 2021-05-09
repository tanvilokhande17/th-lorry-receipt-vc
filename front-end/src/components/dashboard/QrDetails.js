import React, {useState, useEffect, useRef} from "react";
import icon from "./../../images/icon2.jpg";
import axios from "axios";
import {VERIFICATION_URL} from "../util/Constant";

const QrDetails = () => {
  const {vcId, status} = useState(null);
  const [lrDetails, setLrDetails] = useState(null);
  const [vcStatus, setVcStatus] = useState(null);
  let Path = window.location.pathname;
  console.log(Path);
  Path = Path.split("/", 3);
  console.log(Path[2]);
  let passKey = Path[2];

  useEffect(() => {
    if (passKey) {
      axios.get(VERIFICATION_URL + "/" + passKey).then(
        response => {
          console.log(response.data);
          if (response.status === 200) {
            if (response.data.vcDetails) {
              const res = response.data.vcDetails.credentialSubject;

              console.log(res);
              setVcStatus(response.data.lorryReceipt.status);
              setLrDetails({...res, vcId: vcId, status: status});
            }
          }
        },
        error => {
          console.log(error);
        }
      );
    }
  }, []);

  return (
    <div className="lrdetails-container">
      <div style={{display: "flex", flexDirection: "row"}}>
        <span className="lrdetails-header" style={{flex: 4}}>
          Lorry Receipt Details
        </span>
      </div>

      {lrDetails !== null ? (
        <LRDetails lrDetails={lrDetails} vcStatus={vcStatus} />
      ) : (
        <div className="lrDetails-content">
          <span className="lrdetails-header" style={{flex: 4}}>
            Lorry Receipt not found
          </span>
        </div>
      )}
    </div>
  );
};

export default QrDetails;

export const LRDetails = ({lrDetails, vcStatus}) => {
  console.log(vcStatus);
  return (
    <>
      {lrDetails ? (
        <div className="lrDetails-content">
          <div className="lrDetails-header">
            <div style={{flex: 1}}>
              <img style={{maxHeight: 75}} src={icon} />
            </div>

            <div className="lrDetails-company">
              {lrDetails.data.transporter.name}
              <div className="lrDetails-address">
                {lrDetails.data.transporter.address}
              </div>
            </div>

            <div style={{flex: 1, textAlign: "end"}}>
              <div>
                <span style={{fontWeight: 600}}>Mob No: </span>
                {lrDetails.data.transporter.mobileNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>LR No: </span>
                {lrDetails.data.receiptNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>VC ID: </span>
                {lrDetails.data.vcId}
              </div>
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Consignor:</div>
            <div style={{flex: 3}}>{lrDetails.data.consigner.name}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Date: </span>
              {new Date(lrDetails.data.date).getDate() +
                "/" +
                parseInt(new Date(lrDetails.data.date).getMonth() + 1) +
                "/" +
                new Date(lrDetails.data.date).getFullYear()}
            </div>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Consignee:</div>
            <div style={{flex: 3}}>{lrDetails.data.consignee.name}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Status: </span> {vcStatus}
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Driver:</div>
            <div style={{flex: 3}}>{lrDetails.data.driver.name}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Vehicle Number: </span>
              {lrDetails.data.vehicleNumber}
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1, fontWeight: 600}}>Place of Loading:</div>
            <div style={{flex: 5}}>{lrDetails.data.loadingAddress}</div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1, fontWeight: 600}}>Destination:</div>
            <div style={{flex: 5}}>{lrDetails.data.unloadingAddress}</div>
          </div>
          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <table>
              <thead>
                <tr>
                  <th className="lrDetails-th">Quantity</th>
                  <th className="lrDetails-th">Weight</th>
                  <th className="lrDetails-th">Description</th>
                  <th className="lrDetails-th"></th>
                  <th className="lrDetails-th">Freight charges</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td rowSpan="3" className="lr-td-quantity">
                    {lrDetails.data.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.quantity}
                      </div>
                    ))}
                    <div>&nbsp;</div>
                  </td>
                  <td rowSpan="3" className="lr-td-quantity">
                    {lrDetails.data.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.weight}
                      </div>
                    ))}
                    <div>Total : {lrDetails.totalWeight}</div>
                  </td>
                  <td rowSpan="3" className="lr-td-description">
                    {lrDetails.data.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.description}
                      </div>
                    ))}
                    <div>&nbsp;</div>
                  </td>

                  <td className="lr-td-fright">Freight</td>
                  <td className="lr-td-fright">
                    {lrDetails.data.freightCharge.freight}
                  </td>
                </tr>
                <tr>
                  <td className="lr-td-fright">Advance</td>
                  <td className="lr-td-fright">
                    {lrDetails.data.freightCharge.advance}
                  </td>
                </tr>
                <tr>
                  <td className="lr-td-fright">To Pay/Paid</td>
                  <td className="lr-td-fright">
                    {lrDetails.data.freightCharge.toPay}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}></div>
        </div>
      ) : null}
    </>
  );
};
