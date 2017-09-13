// AccessLayer/portal-access/angular-app/src/app/currency-converter/currency-converter.component.ts
import { Component }                from '@angular/core';
import { ConvertedAmount }          from './converted-amount';
import { CurrencyConverterService } from './currency-converter.service';

@Component({
  selector: 'currency-converter',
  templateUrl: './currency-converter.component.html',
  styleUrls: ['./currency-converter.component.scss'],
  providers: [ CurrencyConverterService ],
})
export class CurrencyConverterComponent {
  errorMessage: string;
  convertedAmount: ConvertedAmount;

  animationTime = 3 * 1000;
  animationRunning = false;

  mode = 'Observable';

  constructor (private currencyConverterService: CurrencyConverterService) {}

  convert(amount: string) {
    if (!amount) { return; }
    this.animationRunning = true;
    var that = this;
    setTimeout(function() {
      that.animationRunning = false;
    }, that.animationTime);

    this.currencyConverterService.convert(amount)
      .subscribe(
        result => {
          this.convertedAmount = result;
          this.errorMessage = null;
        },
        error => {
          this.errorMessage = <any>error;
          this.convertedAmount = null;
        }
      );
  }
}
