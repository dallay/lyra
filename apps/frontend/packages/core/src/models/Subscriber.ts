export interface Subscriber {
	id: string;
	email: string;
	name: string;
	status: string;
}

export interface Subscribers {
	subscribers: Subscriber[];
}
