import React from 'react';
import logo from '../logo/Eco-Assistant_transparent.PNG';
/**
 * Composant qui représente la barre de navigation laérale.
 * */
function BarreNavCore() {
    const listeOnglets = [['Accueil', './'], ['Profil', './'], ['Remplir un questionnaire', '/newproject'], ["Questions proposées", './'], ['Moderation', './'], ['Se déconnecter', './logout']]
    const listeLiens = listeOnglets.map((x) => (
        <a href={x[1]} className="list-group-item list-group-item-action text-center" key={x[0]} aria-current="true">
            <span key="key">{x[0]}</span>
        </a>
    ));

    return (
        <nav id="sidebarMenu" className="col-2 border border-2 border-secondary">
            <div className="position-sticky list-group list-group-flush px-3 pt-4 h-100">
                <img alt="logo eco-assistant" src={logo}></img>
                {listeLiens}
            </div>
        </nav>
    );
}

export default BarreNavCore;