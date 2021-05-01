import axios from "axios";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {useHistory, useLocation} from "react-router";
import {WALLET_CREDENTIALS_URL, API_KEY} from "../util/Constant";
import {getUser} from "../util/User";
import LorryReceipt from "./LorryReceipt";

const LRDetails = () => {
  const location = useLocation();
  const {vcId, status} = location.state;
  const [lrDetails, setLrDetails] = useState(null);
  const history = useHistory();
  const user = getUser();

  useEffect(() => {
    if (vcId && lrDetails === null) {
      axios
        .get(WALLET_CREDENTIALS_URL + vcId, {
          headers: {
            Authorization: "Bearer " + user.accessToken,
            "Api-Key": API_KEY,
          },
        })
        .then(
          response => {
            if (response.status === 200) {
              const res = response.data.credentialSubject.data;
              setLrDetails({...res, vcId: vcId, status: status});
              console.log(lrDetails);
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

      {lrDetails !== null ? <LorryReceipt lrDetails={lrDetails} /> : null}
    </div>
  );
};

export default LRDetails;
