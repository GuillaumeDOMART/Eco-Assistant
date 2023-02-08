import React from 'react';
import logo from '../logo.svg';
/**
 * Composant qui représente la barre de navigation laérale.
 * */
function BarreNavCore() {
    const listeOnglets = ['Accueil', 'Profil', 'Remplir un questionnaire', "Questions proposées", 'Moderation', 'Se déconnecter']
    const listeLiens = listeOnglets.map((x, id) => (
        <a href="./" className="list-group-item list-group-item-action py-2 ripple" key={id} aria-current="true">
            <span key="key">{x}</span>
        </a>
    ))
    return (
        <nav id="sidebarMenu" className="col-2 order-12 collapse d-lg-block sidebar collapse bg-black">
            <div className="position-sticky list-group list-group-flush mx-3 mt-4">
                <img alt="logo react" src={logo} height="100px"></img>
                {listeLiens}
            </div>
        </nav>
    );
}

export default BarreNavCore;