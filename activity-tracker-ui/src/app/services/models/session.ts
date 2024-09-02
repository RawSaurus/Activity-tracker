/* tslint:disable */
/* eslint-disable */
import { Activity } from '../models/activity';
export interface Session {
  activity?: Activity;
  createdAt?: string;
  duration?: string;
  finish?: string;
  info?: string;
  name?: string;
  notes?: Array<string>;
  sessionId?: number;
  start?: string;
  updatedAt?: string;
}
