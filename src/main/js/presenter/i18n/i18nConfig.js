import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import {translations} from "./translations";

/**
 * List of all language available for internationalization.
 * @type {{name: *, key: *}[]}
 */
export const languageList = Object.keys(translations).map(key => {return {key: key, name: translations[key].name}})

const i18n2 = i18n
    .use(initReactI18next) // passes i18n down to react-i18next
    .init({
        resources: translations,
        lng: "en",
        interpolation: {
            escapeValue: false // react already safes from xss
        }
    });

/**
 * Used to handle internationalization.
 */
export default i18n2;