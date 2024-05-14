export class dateFormat {
    constructor() {}
    formatToNewDate(date, dateWithoutConversion) {
        let monthNames = [
            "January", "February", "March",
            "April", "May", "June", "July",
            "August", "September", "October",
            "November", "December"
        ];
        if(dateWithoutConversion && Number.isNaN(date.getMonth()) && dateWithoutConversion.split("-")[2].length == 4) { // ios
            date = new Date(dateWithoutConversion.replace(/(\d\d)\-(\d\d)\-(\d{4})/, "$3-$1-$2"));
        }
        let day = date.getDate();
        let monthIndex = date.getMonth();
        let year = date.getFullYear();
        return monthNames[monthIndex] + ' ' + day + ', ' + year;
    }
    getLatestDate(headerBackorderDateArr) {
        let latestBackorderdate = new Date(Math.min.apply(null,headerBackorderDateArr));
        let day = latestBackorderdate.getDate();
        let monthIndex = latestBackorderdate.getMonth()+1;
        let year = latestBackorderdate.getFullYear();
        return monthIndex+"-"+day+"-"+year;
    }
    comparePastMonths(val, monthsTill){
        let todaysDate = new Date();
        let filterDate = new Date(todaysDate.setMonth(todaysDate.getMonth()-(monthsTill || 6)));
        let selectedDate = new Date(val);
        if(filterDate.getTime() <= selectedDate.getTime()){
            return true;
        }
        return false;
    }
    getOnlyMMDDD(val){
        let monthNames = [
            "January", "February", "March",
            "April", "May", "June", "July",
            "August", "September", "October",
            "November", "December"
        ];
        let date = new Date(val.split(" ")[0]);
        return monthNames[date.getMonth()] + ' ' + date.getDate() ;
    }
    // date pformat is DD/MM/YYYY
    isValidDate(date) {
        let bits = date.split('/');
        let d = new Date(bits[2], bits[1] - 1, bits[0]);
        return (d.getMonth() + 1) == bits[1];
    }
    getYearList(startYear) {
        let currentYear = new Date().getFullYear(),
            years = [];
        startYear = startYear || 1986;

        while (startYear <= currentYear) {
            years.push(startYear++);
        }

        return years;
    }
    compareTwoDates(date1, date2){
        let flag;
        if(new Date(date1.replace(/ /g, "T")) <= new Date(date2.replace(/ /g, "T"))){
            flag = 2;
        }else{
            flag = 1;
        }
        return flag;
    }
}