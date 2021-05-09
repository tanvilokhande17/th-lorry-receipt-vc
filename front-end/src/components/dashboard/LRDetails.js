import axios from "axios";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import {useHistory, useLocation} from "react-router";
import LorryReceiptPDF from "../downloadPdf/LorryReceiptPDF";
import {WALLET_CREDENTIALS_URL, API_KEY} from "../util/Constant";
import {getUser} from "../util/User";
import LorryReceipt from "./LorryReceipt";
import {createPdfFromHtml} from "../downloadPdf/GeneratePDF";
import {createGlobalStyle} from "styled-components";

const LRDetails = () => {
  const location = useLocation();
  const {vcId, status, VCStored, vcUrl} = location.state;
  const [lrDetails, setLrDetails] = useState(null);
  const history = useHistory();
  const user = getUser();

  useEffect(() => {
    if (vcId && VCStored) {
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
    } else if (vcUrl !== undefined) {
      console.log(vcUrl);
      axios.get(vcUrl).then(
        response => {
          if (response.status === 200) {
            const res = response.data.credentialSubject.data;
            setLrDetails({...res, vcId: vcId, status: status});
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
        <span style={{flex: 1, marginRight: 10}}>
          {VCStored ? <DownloadPDF lrDetails={lrDetails} /> : null}
        </span>
        <Button
          style={{flex: 1, marginBottom: 10}}
          onClick={() => history.goBack()}
        >
          Back
        </Button>
      </div>

      {lrDetails !== null ? <LorryReceipt lrDetails={lrDetails} /> : null}
    </div>
  );
};

export default LRDetails;

const DownloadPDF = ({lrDetails}) => {
  const [printContent, setPrintContent] = useState(1);
  return (
    <>
      <Global />
      <Button
        variant="primary"
        block
        onClick={() => createPdfFromHtml(printContent)}
      >
        Download
      </Button>
      <div id="print" className="A4">
        <div style={{position: "fixed", top: "200vh"}}>
          <div ref={el => setPrintContent(el)}>
            <LorryReceiptPDF lrDetails={lrDetails} />
          </div>
        </div>
      </div>
    </>
  );
};

const Global = createGlobalStyle`
  /* paper.css */
  https://github.com/cognitom/paper-css

  /* @page { margin: 0 } */
  #print {
    margin: 0;
    font-family: "IPAexGothic", sans-serif;
  }
  .sheet {
    margin: 0;
    overflow: hidden;
    position: relative;
    box-sizing: border-box;
    page-break-after: always;
  }
  
  
  /** Paper sizes **/
  #print.A3               .sheet { width: 297mm; height: 419mm }
  #print.A3.landscape     .sheet { width: 420mm; height: 296mm }
  #print.A4               .sheet { width: 210mm; height: 296mm }
  #print.A4.landscape     .sheet { width: 297mm; height: 209mm }
  #print.A5               .sheet { width: 148mm; height: 209mm }
  #print.A5.landscape     .sheet { width: 210mm; height: 147mm }
  #print.letter           .sheet { width: 216mm; height: 279mm }
  #print.letter.landscape .sheet { width: 280mm; height: 215mm }
  #print.legal            .sheet { width: 216mm; height: 356mm }
  #print.legal.landscape  .sheet { width: 357mm; height: 215mm }
  
  /** Padding area **/
  .sheet.padding-10mm { padding: 10mm }
  .sheet.padding-15mm { padding: 15mm }
  .sheet.padding-20mm { padding: 20mm }
  .sheet.padding-25mm { padding: 25mm }
  
  /** For screen preview **/
  @media screen {
    body {
      background: #E0E0E0;
      height: 100%;
    }
    .sheet {
      background: #FFFFFF;
      /* margin: 5mm auto; */
      padding: 5mm 0;
    }
  }
  
  /** Fix for Chrome issue #273306 **/
  @media print {
    #print.A3.landscape            { width: 420mm }
    #print.A3, #print.A4.landscape { width: 297mm }
    #print.A4, #print.A5.landscape { width: 210mm }
    #print.A5                      { width: 148mm }
    #print.letter, #print.legal    { width: 216mm }
    #print.letter.landscape        { width: 280mm }
    #print.legal.landscape         { width: 357mm }
  }
`;
