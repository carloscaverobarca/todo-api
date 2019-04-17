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
export class UserCommunicationService {

  // Message types
  readonly ERROR = 'danger';
  readonly INFO = 'light';
  readonly SUCCESS = 'success';

  // The messges array accepts message: { text: any, type: string };
  messages: Array<any> = [];

  createMessage(type: string, msg: any) {
    console.log(type + ': ' + msg);
    this.messages.push({ text: msg, type: type });
    // The message lasts 3 seconds (3000 miliseconds)
    setTimeout(() => {
      this.messages.shift();
    }, 3000);
  }
}
