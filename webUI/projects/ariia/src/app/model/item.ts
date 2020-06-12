import { RangeInfo } from './range-info';
import { NetworkSession } from './network-session';

export class Item {
    id: string = '';
    url: string = '';
    redirectUrl: string = '';
    filename: string = '';
    state: string = '';
    saveDirectory: string = '';
    headers: Map<string, string[]> = new Map();
    rangeInfo: RangeInfo = new RangeInfo();
    speedOfTCPReceive: number = 0;
    reminningTime: number = 0;

    constructor(item: Item) {
        if (item) {
            this.update(item);
        }
    }

    update(item: Item) {
        this.id = item.id;
        this.url = item.url;
        this.redirectUrl = item.redirectUrl;
        this.filename = item.filename;
        this.state = item.state;
        this.saveDirectory = item.saveDirectory;
        this.rangeInfo.update(item.rangeInfo);
        this.speedOfTCPReceive = item.speedOfTCPReceive;
        this.reminningTime = item.reminningTime;
        Object.keys(item.headers).forEach((keys) => {
            this.headers.set(keys, item.headers[keys]);
        });
    }
    
    netwotkUpdate(item: Item) {
        this.state = item.state;
        this.rangeInfo.update(item.rangeInfo);
        this.speedOfTCPReceive = item.speedOfTCPReceive;
        this.reminningTime = item.reminningTime;
    }
    
    toString(): string {
        return `${this.filename} - ${this.rangeInfo.fileLength} - ${this.state}`;
    }
}
