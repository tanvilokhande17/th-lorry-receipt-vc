import axios from "axios";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {useHistory, useLocation} from "react-router";
import {WALLET_CREDENTIALS_URL} from "../util/Constant";
import {getUser} from "../util/User";
import LorryReceipt from "./LorryReceipt";

const LRDetails = () => {
  const location = useLocation();
  const lrDetails = location.state.lrDetails;
  const [lrDetail, setLrDetail] = useState();
  const history = useHistory();
  const user = getUser();

  useEffect(() => {
    if (lrDetails && lrDetails.vcId) {
      axios
        .get(WALLET_CREDENTIALS_URL + lrDetails.vcId, {
          headers: {Authorization: "Bearer " + user.accessToken},
        })
        .then(
          response => {
            if (response.status === 200) {
              if (
                response.data.lorryReceipts !== undefined &&
                response.data.lorryReceipts !== null
              ) {
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
      <div className="lrdetails-header">
        <span style={{flex: 5}}>Lorry Receipt Details</span>
        <Button style={{flex: 1}} onClick={() => history.goBack()}>
          Back
        </Button>
      </div>

      {lrDetails ? <LorryReceipt lrDetails={lrDetails} /> : null}
    </div>
  );
};

export default LRDetails;
