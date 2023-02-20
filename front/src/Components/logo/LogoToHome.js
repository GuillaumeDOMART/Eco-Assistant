import {Image} from "react-bootstrap";
import logo from "./Eco-Assistant_transparent.PNG";
import React from "react";

/**
 * Return the Eco-Assistant logo as a link that redirect to the parameter link
 */
export default function LogoToHome(){
    return(
        <a className="p-4" href="/profil">
            <Image fluid src={logo} alt="logo eco-assistant"/>
        </a>
    );
}