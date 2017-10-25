# spring-package-sorter
## Package Sorter

My first project that attempts to tackle a test from an employment opportunity (which I unfortunately wasn't successful) given to me not so long ago. Case scenario follows:

> A company sells hundreds of products on-line and customer place orders from all over the world, just like eBay. Each product has a different weight and price.
>
> Every customer can order any number of different products that need to be separated into one or more packages (each containing one or more products) and then collected by the courier company for delivery to the customer.
>
> However, there are certain rules for each package that must be followed:
> 1. The total cost of all products within a single package cannot exceed $250 for international customs purposes.
> 2. If multiple packages for the same order are required, then the weight of each package should have the lowest possible shipping cost for the order and be as evenly distributed as possible.
>
>
> Create a page which has the following:
> 1. A simple vertical list of products (in the format Name - price - weight), with a check box next to items
> 2. A button saying "Place order"
> 3. When the user clicks on "Place order", the selected items should be submitted to the same page and the above rules should be applied to potentially divide this order into multiple packages
> 4. Display the output result on the same page. Below is a sample output on how it should look like:
>
> This order has following packages:
>
> Package 1
> Items - Item 1, Item 3, item 7
> Total weight - 510g
> Total price - $240
> Courier price - $15
>
> Package 2
> Items - Item 4, Item 6, item 2
> Total weight - 530g
> Total price - $160
> Courier price - $15
