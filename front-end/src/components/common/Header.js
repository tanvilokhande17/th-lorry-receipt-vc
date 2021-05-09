import React ,  {useEffect, useState} from "react";

import {saveUser, getUser} from "../util/User";
import logo from "./../../images/logo.jpg";
import logout from "./../../images/logout.png";
import {Button} from "react-bootstrap";
import {useHistory} from "react-router-dom";
import PolicyDocument from "./PolicyDocument";


const Header = () => {
  const user = getUser();
  const history = useHistory();
  const [showResults, setShowResults] = React.useState(true)
  const [submitDisabled, setSubmitDisabled] = useState(true);


  const logoutHandler = () => {
    saveUser("", "", "", "", false);
    window.location.pathname = "/";
  };
  const PolicyDocument1 = (e) => {
    e.preventDefault();
    window.open('/projectDetails')
    //window.location.pathname = "/PolicyDocument";
  };
  return (

    <div className="header">
      <span style={{flex: 5}}>
        <img className="header-img" src={logo} />
      </span>
      {user && user.isLogin === "true" ? (
        <span style={{flex: 1, display: "flex", direction: "row"}}>
          <div style={{flex: 2}}>
            {user.name} <br /> {user.role}
          </div>
          <div style={{flex: 1.5}}>
            <img className="logout-img" src={logout} onClick={logoutHandler} />
          </div>
        </span>
      ) : null}
      
       {showResults && user.isLogin === "false" ? (
      <div style={{flex: 0.5}} className={`policy-document  ${submitDisabled ? "show" : "hidden"}`}>
      <Button
          className={`policy-document  ${submitDisabled ? "show" : "hidden"}`}
          variant="light"
          onClick={PolicyDocument1}
        >
         Project Details
        </Button>
          </div>
          ) : null}
    </div>
  );
};
export default Header;
