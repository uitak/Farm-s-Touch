
const resetWeather = document.getElementById("resetWeather");
document.querySelector('#resetWeather').addEventListener('click', weatherForcast);

function weatherForcast() {
    function success(position) {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;

        const status = document.querySelector('#status');

        const grid = dfs_xy_conv("toXY", latitude, longitude);
        const gridX = grid.x;
        const gridY = grid.y;

        const baseDateTime = shortTermDate();
        const baseDate = baseDateTime.date;
        const baseTime = baseDateTime.time;

        parsingShortTermForecast(baseDate, baseTime, gridX, gridY);

        findAddress(latitude, longitude)
            .then(function (ret) {
                status.textContent = ret['address'];
                parsingMidTermForecast(ret['region']);
            });

    }
    function error() {
        alert('Unable to retrieve your location');        
    }
    if (!navigator.geolocation) {
        console.log('Geolocation is not supported by your browser');
    } else {
        navigator.geolocation.getCurrentPosition(success, error);
    }

}

// LCC DFS 좌표변환을 위한 기초 자료(기상청 제공)
const RE = 6371.00877;    // 지구 반경(km)
const GRID = 5.0;         // 격자 간격(km)
const SLAT1 = 30.0;       // 투영 위도1(degree)
const SLAT2 = 60.0;       // 투영 위도2(degree)
const OLON = 126.0;       // 기준점 경도(degree)
const OLAT = 38.0;        // 기준점 위도(degree)
const XO = 43;            // 기준점 X좌표(GRID)
const YO = 136;           // 기1준점 Y좌표(GRID)

// LCC DFS 좌표변환 ( code : 
//          "toXY"(위경도->좌표, v1: 위도, v2: 경도), 
//          "toLL"(좌표->위경도, v1: x좌표, v2: y좌표) )
function dfs_xy_conv(code, v1, v2) {
    let DEGRAD = Math.PI / 180.0;
    let RADDEG = 180.0 / Math.PI;

    let re = RE / GRID;
    let slat1 = SLAT1 * DEGRAD;
    let slat2 = SLAT2 * DEGRAD;
    let olon = OLON * DEGRAD;
    let olat = OLAT * DEGRAD;

    let sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
    let sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
    let ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
    ro = re * sf / Math.pow(ro, sn);
    let rs = {};
    if (code == "toXY") {
        rs['lat'] = v1;
        rs['lng'] = v2;
        let ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        let theta = v2 * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        rs['x'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs['y'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
    }
    else {
        rs['x'] = v1;
        rs['y'] = v2;
        let xn = v1 - XO;
        let yn = ro - v2 + YO;
        ra = Math.sqrt(xn * xn + yn * yn);
        if (sn < 0.0) - ra;
        let alat = Math.pow((re * sf / ra), (1.0 / sn));
        alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

        if (Math.abs(xn) <= 0.0) {
            theta = 0.0;
        }
        else {
            if (Math.abs(yn) <= 0.0) {
                theta = Math.PI * 0.5;
                if (xn < 0.0) - theta;
            }
            else theta = Math.atan2(xn, yn);
        }
        let alon = theta / sn + olon;
        rs['lat'] = alat * RADDEG;
        rs['lng'] = alon * RADDEG;
    }
    return rs;
}

function midTermDate() {
    let ret;
    const nowDate = new Date();

    let time = nowDate.baseTime();
    if (time > '1800') {
        ret = nowDate.baseDate() + '1800';
    }
    else if (time > '0600') {
        ret = nowDate.baseDate() + '0600';
    }
    else {
        const yesterday = new Date(nowDate.setDate(nowDate.getDate() - 1));
        ret = yesterday.baseDate() + '1800';
    }

    return ret;
}

function shortTermDate() {
    let ret = {};
    const nowDate = new Date();

    ret['date'] = nowDate.baseDate();
    ret['time'] = nowDate.baseTime();

    if (ret['time'] > '0210') {
        ret['time'] = '0200';
    }
    else {
        const yesterday = new Date(nowDate.setDate(nowDate.getDate() - 1));
        ret['date'] = yesterday.baseDate();
        ret['time'] = '2300';
    }

    return ret;
}

Date.prototype.baseDate = function () {
    const yyyy = this.getFullYear().toString();

    const month = this.getMonth() + 1;
    const mm = month < 10 ? '0' + month.toString() : month.toString();

    const date = this.getDate();
    const dd = date < 10 ? '0' + date.toString() : date.toString();

    return yyyy + mm + dd;
}

Date.prototype.baseTime = function () {
    const hours = this.getHours();
    const hh = hours < 10 ? '0' + hours.toString() : hours.toString();

    const minutes = this.getMinutes();
    const mm = minutes < 10 ? '0' + minutes.toString() : minutes.toString();

    return hh + mm;
}

function parsingShortTermForecast(baseDate, baseTime, gridX, gridY) {
    const requestURL = 'https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst'
        + '?serviceKey=%2BQhBNApBwkdAen%2FLBcs7ZpTTD%2FQv8iejgHqd8cwb5MkqK0PW8JQDdov1vyQgXzgo9iK5az8snCrX4fUVSzcJdA%3D%3D'
        + '&pageNo=1'
        + '&numOfRows=1000'
        + '&dataType=JSON'
        + '&base_date=' + baseDate
        + '&base_time=' + baseTime
        + '&nx=' + gridX
        + '&ny=' + gridY;

   //console.log(requestURL);

    const request = new XMLHttpRequest();
    request.open('GET', requestURL);
    request.responseType = 'json';
    request.send();

    request.onload = function () {
        const weather = request.response;
        showShortTermWeather(weather.response.body.items);
    }
}

function showShortTermWeather(jsonObj) {
    let weatherList = [];
    let cnt = 0;
    const nowDate = new Date();

    const climateList = jsonObj.item;
    for (const climate of climateList) {
        if (climate.category == 'TMN') {
            weatherList.push(climate.fcstValue.split('.')[0]);
        }
        if (climate.category == 'TMX') {
            weatherList.push(climate.fcstValue.split('.')[0]);
        }
    }

    for (let i = 0; i < weatherList.length; i=i+2) {
        //let weatherItem = document.querySelector(`#temperature-${cnt++}`);

        //let temp = document.createElement('h6');
        //temp.classList.add('card-title');
        let temp = document.querySelector(`#temperature-${cnt}`);
        temp.textContent = weatherList[i] + '°C / ' + weatherList[i+1] + '°C';
        //weatherItem.appendChild(temp);

        if (i == 0) {
            let day = nowDate;
            let mmdd = day.baseDate().substring(4);
            //let md = document.createElement('span');
            let md = document.querySelector(`#date-${cnt++}`);
            if (day.getDay() == 0) {
                md.style.color = "red";
            }
            if (day.getDay() == 6) {
                md.style.color = "blue";
            }
            md.textContent = mmdd.substring(0, 2) + '/' + mmdd.substring(2, 4) + ' (' + dayTransfer(day.getDay()) + ')';
            //weatherItem.appendChild(md);
        }
        else {
            let day = new Date(nowDate.setDate(nowDate.getDate() + 1));
            let mmdd = day.baseDate().substring(4);
            //let md = document.createElement('span');
            let md = document.querySelector(`#date-${cnt++}`);
            if (day.getDay() == 0) {
                md.style.color = "red";
            }
            if (day.getDay() == 6) {
                md.style.color = "blue";
            }
            md.textContent = mmdd.substring(0, 2) + '/' + mmdd.substring(2, 4) + ' (' + dayTransfer(day.getDay()) + ')';
            //weatherItem.appendChild(md);
        }
    }

}

function regionToSearchForm(siDo, siGunGu) {
    ret = {};

    if (siDo.indexOf('특별시') !== -1) {
        ret['siDo'] = siDo.slice(0, siDo.length - 3);
    }
    else if (siDo.indexOf('광역시') !== -1) {
        ret['siDo'] = siDo.slice(0, siDo.length - 3);
    }
    else if (siDo.indexOf('특별자치시') !== -1) {
        ret['siDo'] = siDo.slice(0, siDo.length - 5);
    }
    else {
        let pos = -1;
        for (let i = 0; i < siGunGu.length; ++i) {
            if (siGunGu[i] == ' ') {
                pos = i;
                break;
            }
        }

        let siGun;
        if (pos !== -1) {
            siGun = siGunGu.slice(0, i);
        }
        else {
            siGun = siGunGu;
        }

        if (siGun.indexOf('시') !== -1 || siGun.indexOf('군') !== -1) {
            ret['siGun'] = siGun.slice(0, siGun.length - 1);
        }
    }

    return ret;
}

function findAddress(latitude, longitude) {
    return new Promise(function (resolve, reject) {
        let xhr = new XMLHttpRequest();
        xhr.open("GET", `https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?input_coord=WGS84&output_coord=WGS84&x=${longitude}&y=${latitude}`, true);
        xhr.setRequestHeader("Authorization", "KakaoAK 0f7d2ea2999ab51836b3c5329c4e6097");
        xhr.responseType = 'json';
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                const res = xhr.response;
                const list = res.documents;

                let ret = {};
                let regionObj;
                for (const address of list) {
                    if (address.region_type == "H") {
                        regionObj = address;
                    }
                }
                ret['address'] = regionObj.address_name;

                const siDo = regionObj.region_1depth_name;
                const siGunGu = regionObj.region_2depth_name;

                let temp = regionToSearchForm(siDo, siGunGu);
                if (typeof temp['siDo'] !== "undefined") {
                    ret['region'] = temp['siDo'];
                }
                else {
                    ret['region'] = temp['siGun'];
                }

                //console.log(ret['address']);
                //console.log(ret['region']);

                resolve(ret);
            }
        }
        xhr.send();
    });
}

function parsingMidTermForecast(regionName) {
    const jsonObj = JSON.parse(regionCodeData);

    let regionCode, date;
    regionCodeList = jsonObj.regionCode;
    for (const code of regionCodeList) {
        if (Object.getOwnPropertyNames(code)[0] == regionName) {
            regionCode = code[`${regionName}`];
            break;
        }
    }
    date = midTermDate();

    const requestURL = 'https://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa'
        + '?serviceKey=%2BQhBNApBwkdAen%2FLBcs7ZpTTD%2FQv8iejgHqd8cwb5MkqK0PW8JQDdov1vyQgXzgo9iK5az8snCrX4fUVSzcJdA%3D%3D'
        + '&pageNo=1'
        + '&numOfRows=10'
        + '&dataType=JSON'
        + `&regId=${regionCode}`
        + `&tmFc=${date}`;

    //console.log(requestURL);

    const request = new XMLHttpRequest();
    request.open('GET', requestURL);
    request.responseType = 'json';
    request.send();

    request.onload = function () {
        const weather = request.response;
        showMidTermWeather(weather.response.body.items);
    }
}

function showMidTermWeather(jsonObj) {
    const nowDate = new Date();
    
    const year = nowDate.getFullYear();
    const month =  nowDate.getMonth();
    const day = nowDate.getDate();

    const climate = jsonObj.item[0];
    for (let i = 4; i < 8; ++i) {
        //let weatherItem = document.querySelector(`#temperature-${i-1}`);

        //let temp = document.createElement('h6');
        let temp = document.querySelector(`#temperature-${i-1}`);
        temp.classList.add('card-title');
        temp.textContent = climate[`taMin${i}`] + '°C / ' + climate[`taMax${i}`] + '°C';
        //weatherItem.appendChild(temp);

        let d = new Date(year, month, day + i - 1);
        //console.log('d: ' + d);
        let mmdd = d.baseDate().substring(4);
        console.log('mmdd: ' + mmdd);
        //let md = document.createElement('span');
        let md = document.querySelector(`#date-${i-1}`);
        if (d.getDay() == 0) {
            md.style.color = "red";
        }
        if (d.getDay() == 6) {
            md.style.color = "blue";
        }
        md.textContent = mmdd.substring(0, 2) + '/' + mmdd.substring(2, 4) + ' (' + dayTransfer(d.getDay()) + ')';
        //weatherItem.appendChild(md);
    }
}

function dayTransfer(day) {
    let ret = '';
    if (day == 0) {
        ret = '일';
    } 
    else if (day == 1) {
        ret = '월';
    }
    else if (day == 2) {
        ret = '화';
    }
    else if (day == 3) {
        ret = '수';
    }
    else if (day == 4) {
        ret = '목';
    }
    else if (day == 5) {
        ret = '금';
    }
    else if (day == 6) {
        ret = '토';
    }
    return ret;
}