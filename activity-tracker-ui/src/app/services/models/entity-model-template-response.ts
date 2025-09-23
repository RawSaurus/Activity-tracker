/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
export interface EntityModelTemplateResponse {
  '_links'?: Links;
  category?: 'ACTIVITY' | 'SPORT';
  info?: string;
  name?: string;
  type?: string;
}
