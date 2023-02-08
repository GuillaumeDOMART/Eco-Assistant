import React from 'react';
import logo from '../logo.svg';

function BarreNavCore() {
    return (
        <header className="col-2 order-12">
            <nav id="sidebarMenu" className="collapse d-lg-block sidebar collapse bg-black">
                <div className="position-sticky">
                    <div className="list-group list-group-flush mx-3 mt-4">
                        <img alt="logo react" src={logo} height="100px"></img>
                        {['Accueil', 'Profil', 'Remplir un questionnaire', 'Questions proposées', 'Moderation', 'Se déconnecter'].map(placement => (
                        <a href="#" className="list-group-item list-group-item-action py-2 ripple" aria-current="true">
                            <i className="fas fa-tachometer-alt fa-fw me-3"></i><span>{placement}</span>
                        </a>
                        ))}
                    </div>
                </div>
            </nav>
        </header>
    );
}

export default BarreNavCore;