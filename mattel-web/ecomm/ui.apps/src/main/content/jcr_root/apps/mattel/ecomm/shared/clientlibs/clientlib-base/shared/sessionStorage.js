/*
    fn.setStorage(storageName, obj);
    fn.getStorage(storageName);
    fn.deleteStorage(storageName);
*/
export const setStorage = (setName = null, obj = {}) => {
    if (getStorage(setName) != null) deleteStorage(setName);
    sessionStorage.setItem(setName, JSON.stringify(obj));
};

export const getStorage = name => {
    return JSON.parse(sessionStorage.getItem(name));
};

export const deleteStorage = name => {
    window.sessionStorage.removeItem(name)
};

export default {};