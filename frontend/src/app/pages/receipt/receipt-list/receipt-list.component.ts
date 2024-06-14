import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ReceiptService } from 'src/app/core/services/receipt.service';

@Component({
  selector: 'app-receipt-list',
  templateUrl: './receipt-list.component.html',
  styleUrls: ['./receipt-list.component.scss']
})
export class ReceiptListComponent implements OnInit {
  
  breadCrumbItems: Array<{}>;
  receipts: any[];

  constructor(private toastrService: ToastrService, private receiptService: ReceiptService) { }

  ngOnInit(): void {
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Receipts', active: true }];
    this.all();
  }

  async onCopy(text: string) {
    console.log(text + 'copied!!')
    try {
      await navigator.clipboard.writeText(text);
      // Optional: Provide feedback or perform additional actions upon successful copy
      this.toastrService.info(text, 'Copied to Clipboard!');
    } catch (error) {
      console.error("Failed to copy to clipboard:", error);
      // Optional: Handle and display the error to the user
      this.toastrService.error(text, 'Not Copied!');
    }
  }

  all() {
    this.receiptService.all()
    .subscribe(result => {
      console.log(result)
      this.receipts = result;
    })
  }


}
