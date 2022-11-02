const toPrettyDate = date => {
    const toMonthName = monthNumber => {
        const date = new Date();
        date.setMonth(monthNumber - 1);
        return date.toLocaleString([], { month: 'long',});
    }

    const year = date.slice(0, 4)
    const month = toMonthName(date.slice(5, 7))
    const day = date.slice(8, 10)
    const time = date.slice(11, 16)

    return day + " " + month + " " + year + ", " + time
}

export default toPrettyDate