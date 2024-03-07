import React from "react";

/**
 * Responsible for rendering the top bar.
 * @param props - props.
 * @param {String} props.username - the username of the logged-in user, or null if no one is logged in.
 * @param {Function} props.onLogout - callback for when the logged-in user clicks the log-out button.
 * @param {JSX.Element[]} props.children - the additional children of the top bar
 * @returns {JSX.Element} the rendered top bar.
 */
export function TopBarView(props) {

    return (
        <div id={"top-bar"}>
            <div className={"left"}>
                <h1>Go Hire</h1>
            </div>
            <div className={"right"}>
                {props.username && <p>{props.t('logged-in-as')}: {props.username}</p>}
                {props.children}
                {props.username && <button onClick={props.onLogout}>{props.t('logout')}</button>}
            </div>
        </div>
    )
}