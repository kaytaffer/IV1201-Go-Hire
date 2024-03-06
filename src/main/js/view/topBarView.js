import React from "react";

// TODO update javadoc
/**
 * Responsible for rendering the top bar.
 * @param props - props.
 * @param {String} props.username - the username of the logged-in user, or null if no one is logged in.
 * @param {Function} props.onLogout - callback for when the logged-in user clicks the log-out button.
 * @returns {JSX.Element} the rendered top bar.
 */
export function TopBarView(props) {

    function languageSelected() {
        props.onLanguageChange(document.getElementById("language-picker").value)
    }

    return (
        <div id={"top-bar"}>
            <div className={"left"}>
                <h1>Go Hire</h1>
            </div>
            <div className={"right"}>
                {props.username && <p>{props.t('logged-in-as')}: {props.username}</p>}
                <select id="language-picker" name="language-picker" onChange={languageSelected}>
                    {props.languageList.map(language =>
                        <option value={language.key} key={language.key}>
                            {language.name}
                        </option>)}
                </select>
                {props.username && <button onClick={props.onLogout}>{props.t('logout')}</button>}
            </div>
        </div>
    )
}