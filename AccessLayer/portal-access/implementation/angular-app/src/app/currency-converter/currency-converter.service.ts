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
  constructor(private http:Http) {}

  private currencyConverterServiceUrl = '/exchange/convert';

  private extractData(res: Response) {
    return res.json() || {};
  }

  convert(value: string, currency: string) : Observable<ConvertOut[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.currencyConverterServiceUrl, { value, currency }, options)
      .map(this.extractData)
      .catch((error:any) => Observable.throw(error.json().message + " (" + error.json().exception + ")"));
  }
}
