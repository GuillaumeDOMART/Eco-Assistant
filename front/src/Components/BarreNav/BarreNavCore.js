import React, {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import {ListGroup} from "react-bootstrap";
import LogoToHome from "../logo/LogoToHome";
/**
 * Composant qui représente la barre de navigation laérale.
 * */
function BarreNavCore() {
    const listeOnglets = [['Accueil', '/profil'], ['Profil', '/infoProfil'], ['Remplir un questionnaire', '/newproject'], ["Questions proposées", './'], ['Moderation', './'], ['Se déconnecter', './logout']]

    const listeLiens = listeOnglets.map((x) => (
        <BarreNav_GroupItem key={x[0]} {...x}/>
    ));

    return (
        <nav id="sidebarMenu" className="col-2 border border-2 border-secondary">
            <div className="position-sticky list-group list-group-flush px-3 pt-4 h-100">
                <LogoToHome/>
                <ListGroup variant="flush">
                    {listeLiens}
                </ListGroup>
            </div>
        </nav>
    );
}

/**
 * Return a list group item, with the correct color
 */

function BarreNav_GroupItem(pair){
    const [isActive, setActive] = useState(false)
    const localisation = useLocation()
    console.log(localisation)

    useEffect(() => {
        if(pair[0] === localisation.pathname) {
        setActive(true)
        }
    });

    if(isActive) {
        return <ListGroup.Item variant="primary" action href={pair[1]}>{pair[0]}</ListGroup.Item>;
    } else return <ListGroup.Item action href={pair[1]}>{pair[0]}</ListGroup.Item>;
}

export default BarreNavCore;