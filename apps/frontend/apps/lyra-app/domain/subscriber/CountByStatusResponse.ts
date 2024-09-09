export interface CountByStatusResponse{
  count: number;
  status: string;
}

export interface SubscriberCountByStatusResponse {
  data: CountByStatusResponse[];
}
