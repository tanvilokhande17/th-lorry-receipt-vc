import React from "react";
import logo from "./../../images/thlogo.jpg";

const Footer = () => {
  return (
    <div className="footer">
      <p className="poweredBy">
        <i>powerd by</i>
      </p>
      <img className="footer-img" src={logo}></img>
    </div>
  );
};
export default Footer;
