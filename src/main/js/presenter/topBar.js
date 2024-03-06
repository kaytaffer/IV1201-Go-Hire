import React from "react";
import {TopBarView} from "../view/topBarView";
import {languageList} from "../view/i18n/i18nConfig";
import {useTranslation} from "react-i18next";

/**
 * Responsible for the logic of the top bar.
 * @param props props
 * @param {Object} props.user the current logged-in user.
 * @param {function} props.onLogout - called when user logs out.
 * @returns {JSX.Element} the rendered top bar.
 */
export function TopBar(props) {
    const { t, i18n } = useTranslation();

    function onLanguageChange(language) {
        i18n.changeLanguage(language)
    }

    return <TopBarView username={props.user && props.user.username}
                       onLogout={props.onLogout}
                       languageList={languageList}
                       onLanguageChange={onLanguageChange}
                       t={t}/>
}