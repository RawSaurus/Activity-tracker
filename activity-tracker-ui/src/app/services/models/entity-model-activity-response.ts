/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
export interface EntityModelActivityResponse {
  '_links'?: Links;
  category?: 'ACTIVITY' | 'SPORT';
  info?: string;
  name?: string;
  type?: string;
}
