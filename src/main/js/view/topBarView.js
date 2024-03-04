import React from "react";
import { useTranslation } from 'react-i18next';

/**
 * Responsible for rendering the top bar.
 * @param props - props.
 * @param {String} props.username - the username of the logged-in user, or null if no one is logged in.
 * @param {Function} props.onLogout - callback for when the logged-in user clicks the log-out button.
 * @returns {JSX.Element} the rendered top bar.
 */
export function TopBarView(props) {
    const { t } = useTranslation();

    return (
        <div id={"top-bar"}>
            <div className={"left"}>
                <h1>Go Hire</h1>
            </div>
            <div className={"right"}>
                {props.username && <p>Logged in as: {props.username}</p>}
                {props.username && <button onClick={props.onLogout}>{t('logout')}</button>}
            </div>
        </div>
    )
}