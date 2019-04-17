/**
Copyright © 2018  Atos Spain SA. All rights reserved.
This file is part of the Personal Health System (PHS) for HeartMan project.
Personal Health System (PHS) is free software: you can redistribute it and/or modify it under the terms of Apache 2.0.
THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
See README file for the full disclaimer information and LICENSE file for full license information in the project root.
*/
import { Injectable } from '@angular/core';

@Injectable()
export class LoggerService {

  log(msg: any) {
    console.log(msg);
  }

  error(msg: any) {
    console.error(msg);
  }

  warn(msg: any) {
    console.warn(msg);
  }
}
