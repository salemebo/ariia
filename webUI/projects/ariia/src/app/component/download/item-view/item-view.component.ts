import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Item } from '../../../model/item';
import { RangeInfoService } from '../../../service/range-info.service';
import { ItemService } from '../../../service/item.service';
import { faTrash, faDownload, faPauseCircle, faCloudDownloadAlt, faPlayCircle } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'ariia-item-view',
  templateUrl: './item-view.component.html',
  styleUrls: ['./item-view.component.scss'],
  providers: [
    { provide: RangeInfoService }
  ]
})
export class ItemViewComponent implements OnInit {

  @Input() item: Item;
  @Output() delete: EventEmitter<void> = new EventEmitter<void>();

  faTrash = faTrash;
  faDownload = faDownload;
  faPauseCircle = faPauseCircle;
  faCloudDownloadAlt = faCloudDownloadAlt;
  faPlayCircle = faPlayCircle;


  constructor(private rangeInfoService: RangeInfoService, private itemService: ItemService) { }

  ngOnInit(): void {
    this.rangeInfoService.initRangeInfo(this.item.rangeInfo);
  }

  deleteItem() {
    return this.itemService.deleteItem(this.item.id).subscribe(deleted => {
      if (deleted) {
        this.delete.emit();
      }
    });
  }

  startItem() {
    return this.itemService.startItem(this.item.id).subscribe(start => {
      if (start) {
        console.info(`start download item ${this.item}`);
      }
    });
  }

  pauseItem() {
    return this.itemService.pauseItem(this.item.id).subscribe(pause => {
      if (pause) {
        console.info(`pause download item ${this.item}`);
      }
    });
  }

  private percent(): string {
    return this.rangeInfoService.percent().toFixed(2);
  }
  
  itemPercent(): string {
    return `${this.percent()}%`;
  }
  
  itemProgress(): number {
    return +this.percent();
  }

  rangePercent(index: number): string {
    return `${this.rangeInfoService.rangePercent(index).toFixed(2)}%`;
  }

  startPercent(index: number): string {
    return `${this.rangeInfoService.rangeStartPercent(index)}%`;
  }

  endPercent(index: number): string {
    return `${this.rangeInfoService.rangeEndPercent(index)}%`;
  }

  svgFill(index: number): string {
    return this.rangeInfoService.isFinish(index) ? 'green' : 'blue';
  }

  getX(index: number): number {
    return (index % 50) * 15;
  }

  getY(index: number): number {
    return ~~(index / 50) * 15;
  }
  
  getSvgHeight(): number {
  	const rowNum = ~~(this.item?.rangeInfo?.range?.length/50) + (this.item?.rangeInfo?.range?.length%50 > 0 ? 1: 0);
    return rowNum * 15;
  }
}
