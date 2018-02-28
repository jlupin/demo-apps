// AccessLayer/portal-access/angular-app/src/app/currency-converter/currency-converter.service.ts
import { Injectable }              from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';

import { Observable }              from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import { ConvertOut }              from './convert-out'

@Injectable()
export class CurrencyConverterService {
  private pref = "";
  private currencyConverterServiceUrl = '/convert';

  constructor(private http:Http) {
    let fullPath = window.location.pathname;
    let res = fullPath.split("/");
    this.pref = res.length > 2 ? "/" + res[1] : "";
  }

  private extractData(res: Response) {
    return res.json() || {};
  }

  convert(value: string, currency: string) : Observable<ConvertOut[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.pref + this.currencyConverterServiceUrl, { value, currency }, options)
      .map(this.extractData)
      .catch((error:any) => Observable.throw(error.json().message + " (" + error.json().exception + ")"));
  }
}
