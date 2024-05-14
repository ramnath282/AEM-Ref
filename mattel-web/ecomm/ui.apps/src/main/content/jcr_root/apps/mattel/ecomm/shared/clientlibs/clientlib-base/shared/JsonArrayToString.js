export class JsonArrayToString {
    constructor() {}
    toString(arr) {
        let inputArray = new Array(arr);
        return inputArray.join(", "); 
    }
}
