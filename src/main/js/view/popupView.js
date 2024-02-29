import React from "react";

/**
 * Popup container meant to wrap child components as a popup view.
 * @param {boolean} props.open Whether the Popup should currently be shown.
 * @param props.className style defining class name.
 * @param {JSX.Element} props.children the content of the Popup.
 * @param {function} props.onClose What should happen when the user wishes to close the popup. Should be setting
 * boolean is passed to 'props.open' to false.
 * @returns {JSX.Element}
 */
export function PopupView(props) {

    return ( props.open &&
        <div className="popup-background">
            <div className={"popup " + props.className}>
                <button className="close-popup" onClick={props.onClose}>x</button>
                {props.children}
            </div>
        </div>
    )
}