import {Image} from "react-bootstrap";
import logo from "./Eco-Assistant_transparent.PNG";
import React from "react";

export default function LogoToHome(){
    return(
        <a href="/profil">
            <Image fluid src={logo} alt="logo eco-assistant"/>
        </a>
    );
}