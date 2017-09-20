import { Component }                from '@angular/core';
import { ConvertOut }               from './convert-out';
import { CurrencyConverterService } from './currency-converter.service';

@Component({
  selector: 'currency-converter',
  templateUrl: './currency-converter.component.html',
  styleUrls: ['./currency-converter.component.scss'],
  providers: [ CurrencyConverterService ],
})
export class CurrencyConverterComponent {
  value = "";
  currency = "USD";

  errorMessage: string;
  convertOutList: ConvertOut[];

  liveMode = false;

  animationEnabled = true;
  animationTime = 3 * 1000;
  animationRunning = false;

  mode = 'Observable';

  constructor (private currencyConverterService: CurrencyConverterService) {}

  convert() {
    if (!this.value) { return; }
    if (!this.currency) { return; }

    if (this.animationEnabled) {
      this.animationRunning = true;
      var that = this;
      setTimeout(function() {
        that.animationRunning = false;
      }, that.animationTime);
    }

    this.currencyConverterService.convert(this.value, this.currency)
      .subscribe(
        result => {
          this.value = "";
          this.convertOutList = result;
          this.errorMessage = null;
        },
        error => {
          this.value = "";
          this.errorMessage = <any>error;
          this.convertOutList = null;
        }
      );
  }

  startLiveMode() {
    this.liveMode = true;
    this.animationEnabled = false;
    var that = this;
    var run = function() {
      if (!that.liveMode) {
        return;
      }

      console.log("starting run");
      that.value = (Math.random() * 100).toFixed(2);
      that.convert();
      setTimeout(run, 100);
    }
    run();
  }

  stopLiveMode() {
    this.liveMode = false;
  }
}
